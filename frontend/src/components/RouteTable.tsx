import { For, Show, createResource, createSignal } from "solid-js";
import "./RouteTable.css"
import PatientCard from "./PatientCard";
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

export default function RouteTable() {
    const [limit, setLimit] = createSignal(10);
    const [offset, setOffset] = createSignal(0);
    const [testNameFilter, setTestNameFilter] = createSignal("");
    const [indicationFilter, setIndicationFilter] = createSignal("");
    const [summaryFilter, setSummaryFilter] = createSignal("");
    const [specialty, setSpecialty] = createSignal("All");

    const [patients] = createResource(
        () => [limit(), offset(), specialty(), indicationFilter(), summaryFilter(), testNameFilter()] as const,
        ([limit, offset, specialty, indication, summary, testName]) => fetchPatients(limit, offset, specialty, indication, summary, testName)
    );

    const newPatient = () => {
        console.log("Go to new patient view")
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
        <>
            <h1 class="text-center my-2">Patients Info</h1>
            <div class="flex flex-row gap-2">
                <button class='bg-green-500 hover:bg-green-600 text-white py-2 px-4 ml-2 rounded-lg active:bg-green-700' onClick={newPatient}>Create New Patient</button>
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
                <button class="bg-blue-500 hover:bg-blue-600 text-white py-2 px-3 mr-2 rounded-lg active:bg-blue-700" onClick={clearInputFilters}>Clear Filters</button>
            </div>

            <Show when={patients()} fallback={<h2 class="p-4 text-center">Loading...</h2>}>
                <div>
                    <For each={patients()} fallback={<h2 class="p-4 text-center">Oops, no patients with given filters...</h2>}>
                        {(patient: Patient) => (
                            <PatientCard patient={patient} />
                        )}
                    </For>
                </div>
                <div class="flex flex-row">
                    <div class="grow">{/* whitespace */}</div>
                    <button class='bg-gray-500 hover:bg-gray-600 text-white py-2 px-4 rounded-lg active:bg-gray-700 w-1/4' onClick={loadMore}>Load More</button>
                    <div class="grow">{/* whitespace */}</div>
                </div>
            </Show>
        </>
    );
}