import { createEffect, createSignal } from "solid-js";
import "./RouteTable.css"
import { createStore } from "solid-js/store";

const fetchPatient = (id: number) => {
    return fetch(`/api/patients/${id}`)
        .then((resp) => {
            return resp.json()
                .then((data): {} => data);
        });
}


export default function RouteTable() {
    const [patientId, setPatientId] = createSignal<number>(1);
    const [patient, setPatient] = createStore();

    createEffect(() => {
        fetchPatient(patientId()).then((data) => {
            console.log(data);
            setPatient(data);
        });
    })

    const handleButtonClick = () => {
        const input = document.getElementById("patientIdInput") as HTMLInputElement;
        setPatientId(input.valueAsNumber);
    }

    return (
        <>
        <h1>Patient Info</h1>
        <label for="patientId">Patient Id</label>
        <span> </span>
        <input
            type="number"
            min="1"
            placeholder="Enter patient Id"
            id="patientIdInput"
        />
        <button onClick={handleButtonClick}>Lookup</button>
        <div>
            <table>
                <thead>
                    <tr>
                        <th>Key</th>
                        <th>Value</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        Object.entries(patient).map(
                            ([key, value]) => {
                                return (
                                    <tr>
                                        <td>{ key }</td>
                                        <td>{ value }</td>
                                    </tr>
                                );
                            }
                        )
                    }
                </tbody>
                <tfoot></tfoot>
            </table>
        </div>
        </>
    );
}