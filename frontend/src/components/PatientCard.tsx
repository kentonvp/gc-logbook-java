import { Patient } from "~/patient";
import "./PatientCard.css"
import { Dynamic } from "solid-js/web";
import ColorText from "./ColorText";

export default function PatientCard(props: { patient: Patient, deleteCallback: () => void, canDelete: () => boolean}) {
    const deletePatient = async () => {
        console.log(`Deleting patient ${props.patient.id}`);

        await fetch(`/api/patients/${props.patient.id}`, {
            method: "DELETE",
        })
        props.deleteCallback()
    };

    const specialtyColors = {
        GENERAL: () => ColorText({ color: "bg-red-200", text: "General" }),
        PEDIATRIC: () => ColorText({ color: "bg-purple-200", text: "Pediatric" }),
        LAB: () => ColorText({ color: "bg-yellow-200", text: "Lab" }),
        CANCER: () => ColorText({ color: "bg-pink-200", text: "Cancer" }),
        PRENATAL: () => ColorText({ color: "bg-green-200", text: "Prenatal" }),
        OTHER: () => ColorText({ color: "bg-blue-200", text: "Other" })
    }

    return (
        <div class="patient-grid">
            <div class="info-card">
                <p>
                    {/* <b>Id</b> {props.patient.id} <br /> */}
                    <b>Specialty</b> <Dynamic component={specialtyColors[props.patient.specialty]} /> <br />
                    <b>Gender</b> {props.patient.gender} <br />
                    <b>Age</b> {props.patient.age} <br />
                    <b>Indication</b> {props.patient.indication} <br />
                    <b>Date</b> {props.patient.date.slice(0, 10)}
                </p>

            </div>
            <div class="summary-card">
                <p>
                    <b>Summary</b><br />{props.patient.summary}
                </p>
            </div>
            <div class="results-card relative">
                <p>
                    <b>Test Report</b><br />
                    <b>Tests</b> {props.patient.testNames}<br />
                    <b>Results</b> {props.patient.testResults}
                </p>
                <button class='absolute top-0 right-0 px-2 hover:opacity-50' onClick={deletePatient} disabled={!props.canDelete()} title="Delete Log">🅧</button>
            </div>
        </div>
    );
}