import { For, Show, createResource, createSignal } from "solid-js";
import "./PatientsHome.css"
import PatientCard from "./PatientCard";
import NewPatientForm from "./NewPatientForm";
import { Patient } from "~/patient";

const fetchPatients = async (limit: number, offset: number, specialty: string, indication: string, summary: string, testName: string) => {
    const params = new URLSearchParams({
        testName: testName,
        indication: indication,
        summary: summary,
    });
    if (specialty !== "All") {
        params.append("specialty", specialty);
    }
    const res = await fetch(`/api/patients?${params}`);
    return res.json();
}

export default function PatientsHome() {
    const [limit, setLimit] = createSignal(10);
    const [offset, setOffset] = createSignal(0);
    const [testNameFilter, setTestNameFilter] = createSignal("");
    const [indicationFilter, setIndicationFilter] = createSignal("");
    const [summaryFilter, setSummaryFilter] = createSignal("");
    const [specialty, setSpecialty] = createSignal("All");

    const [onNewPatientView, toggleNewPatientView] = createSignal(false);
    const [hasNewPatient, setNewPatient] = createSignal(true);

    const [patients] = createResource(
        () => [hasNewPatient(), limit(), offset(), specialty(), indicationFilter(), summaryFilter(), testNameFilter()] as const,
        ([hasNewPatient, limit, offset, specialty, indication, summary, testName]) => {
            setNewPatient(false);
            return fetchPatients(limit, offset, specialty, indication, summary, testName);
        }
    );

    const succesfulNewPatient = () => {
        toggleNewPatientView(false);
        setNewPatient(true);
    };

    const loadMore = () => {
        setOffset(offset() + limit());
        console.log("Offset=" + offset() + " Limit=" + limit());
    };

    let indicationFilterRef: HTMLInputElement;
    let summaryFilterRef: HTMLInputElement;
    let testNameFilterRef: HTMLInputElement;

    const clearInputFilters = () => {
        setSpecialty("All");
        setIndicationFilter("");
        setSummaryFilter("");
        setTestNameFilter("");

        indicationFilterRef.value = "";
        summaryFilterRef.value = "";
        testNameFilterRef.value = "";
    };

    return (
        <div class="flex flex-col h-full">
            <div>
                <div class={onNewPatientView() ? "opacity-20" : ""}>
                    <h1 class="text-center my-2">Patients Info</h1>
                    <div class="flex flex-row gap-2">
                        <button class='btn-purple ml-2' onClick={() => toggleNewPatientView(true)} disabled={onNewPatientView()}>Create New Patient</button>
                        <div class="grow">{/* whitespace */}</div>
                        <select value={specialty()} onChange={(e) => setSpecialty(e.target.value)}>
                            <option value="All">All</option>
                            <option value="GENERAL">General</option>
                            <option value="PEDIATRIC">Pediatric</option>
                            <option value="LAB">Lab</option>
                            <option value="CANCER">Cancer</option>
                            <option value="PRENATAL">Prenatal</option>
                            <option value="OTHER">Other</option>
                        </select>
                        <input ref={indicationFilterRef} class="filter-box" type="string" placeholder="Indication filter" onChange={(e) => setIndicationFilter(e.target.value)} />
                        <input ref={summaryFilterRef} class="filter-box" type="string" placeholder="Summary filter" onChange={(e) => setSummaryFilter(e.target.value)} />
                        <input ref={testNameFilterRef} class="filter-box" type="string" placeholder="Test name filter" onChange={(e) => setTestNameFilter(e.target.value)} />
                        <button class="btn-purple mr-2" onClick={clearInputFilters} disabled={onNewPatientView()}>Clear Filters</button>
                    </div>

                    <Show when={patients()} fallback={<h2 class="p-4 text-center">Loading...</h2>}>
                        <div>
                            <For each={patients()} fallback={<h2 class="p-4 text-center">Oops, no patients with given filters...</h2>}>
                                {(patient: Patient) => (
                                    <PatientCard patient={patient} canDelete={() => !onNewPatientView()} deleteCallback={() => setNewPatient(true)} />
                                )}
                            </For>
                        </div>
                    </Show>
                </div>
            </div>
            <div class={"absolute inset-1/4 z-10" + (!onNewPatientView() ? " hidden" : "")}>
                <NewPatientForm successCallback={succesfulNewPatient} cancelCallback={() => toggleNewPatientView(false)} />
            </div>
            <div class="grow"> {/* whitspace */} </div>
            <div class={"flex justify-center" + (onNewPatientView() ? " opacity-20" : "")}>
                <button class='btn-purple w-1/4' onClick={loadMore} disabled={onNewPatientView()}>Load More</button>
            </div>
        </div>
    );
}