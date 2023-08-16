import { Patient } from "~/patient";
import "./PatientCard.css"
import { Dynamic } from "solid-js/web";
import ColorText from "./ColorText";

export default function PatientCard(props: { patient: Patient }) {
    const specialtyColors = {
        GENERAL: () => ColorText({ color: "bg-red-200", text: "General" }),
        PEDIATRIC: () => ColorText({ color: "bg-purple-200", text: "Pediatric" }),
        LAB: () => ColorText({ color: "bg-yellow-200", text: "Lab" }),
        CANCER: () => ColorText({ color: "bg-pink-200", text: "Cancer" }),
        PRENATAL: () => ColorText({ color: "bg-green-200", text: "Prenatal" }),
        OTHER: () => ColorText({ color: "bg-blue-200", text: "Other" })
    }

    return (
        <>
            <div class="patient-grid">
                <div class="info-card">
                    <p>
                        <b>Specialty</b> <Dynamic component={specialtyColors[props.patient.specialty]} /> <br/>
                        <b>Gender</b> {props.patient.gender} <br/>
                        <b>Age</b> {props.patient.age} <br/>
                        <b>Indication</b> {props.patient.indication}
                    </p>

                </div>
                <div class="summary-card">
                    <p>
                        <b>Summary</b><br/>{props.patient.summary}
                    </p>
                </div>
                <div class="results-card">
                    <p>
                        <b>Test Report</b><br/>
                        <b>Tests</b> {props.patient.testNames}<br/>
                        <b>Results</b> {props.patient.testResults}
                    </p>
                </div>
            </div>
        </>
    );
}