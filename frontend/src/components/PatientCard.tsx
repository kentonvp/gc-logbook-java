import { Patient } from "~/patient";
import "./PatientCard.css"

export default function PatientCard(props: { patient: Patient }) {
    return (
        <>
            <div class="patient-grid">
                <div class="info-card">
                    <p>
                        <b>Specialty</b> <span class="bg-pink-200">{props.patient.specialty}</span> <br/>
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