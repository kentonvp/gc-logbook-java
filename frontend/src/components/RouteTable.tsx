import { For, Show, createResource, createSignal } from "solid-js";
import "./RouteTable.css"
import PatientCard from "./PatientCard";
import { Patient } from "~/patient";

const fetchPatients = async (limit: number, offset: number, specialty: string, testName: string, indication: string) => {
    const params = new URLSearchParams({
        testName: testName,
        indication: indication
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
        () => [limit(), offset(), specialty(), testNameFilter(), indicationFilter()] as const,
        ([limit, offset, specialty, testName, indication]) => fetchPatients(limit, offset, specialty, testName, indication)
    );

    const newPatient = () => {
        console.log("Go to new patient view")
    }

    const showMore = () => {
        setOffset(offset() + limit());
        console.log("Offset=" + offset() + " Limit=" + limit());
    }

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
            <input class="px-4" type="string" placeholder="Test name filter" onChange={(e) => setTestNameFilter(e.target.value)}/>
            <input class="px-4" type="string" placeholder="Indication filter" onChange={(e) => setIndicationFilter(e.target.value)}/>
            <input class="px-4 mr-2" type="string" placeholder="Summary filter" onChange={(e) => setSummaryFilter(e.target.value)}/>
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
                <button class='bg-gray-400 hover:bg-gray-500 text-white py-2 px-4 rounded-lg active:bg-gray-600 w-1/4' onClick={showMore}>Load More</button>
                <div class="grow">{/* whitespace */}</div>
            </div>
        </Show>
        </>
    );
}