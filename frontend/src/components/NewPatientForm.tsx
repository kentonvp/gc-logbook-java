import { createSignal } from "solid-js";

export default function NewPatientForm(props: {success_callback: () => void, cancel_callback: () => void}) {

    const [specialty, setSpecialty] = createSignal('GENERAL');
    const [gender, setGender] = createSignal('UNKNOWN');
    const [age, setAge] = createSignal(1);
    const [indication, setIndication] = createSignal('');
    const [summary, setSummary] = createSignal('');
    const [testNames, setTestNames] = createSignal('');
    const [testResults, setTestResults] = createSignal('');

    const createPatient = async () => {
        const patient = {
            specialty: specialty(),
            gender: gender(),
            age: age(),
            indication: indication(),
            date: new Date().toISOString(),
            summary: summary(),
            testNames: testNames(),
            testResults: testResults(),
            orderedTests: testNames().length > 0
        };

        console.log(patient);

        await fetch("/api/patients", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(patient)
        })
        props.success_callback()
    };

    return (
    <>
        <div class="grid grid-rows-6">
            <div class="row-span-5">
                <div class="grid grid-cols-6 gap-1 p-1">
                    <div class='col-span-1'>
                        <div class='flex flex-col justify-around bg-pink-500 p-2'>
                            <div>
                                <label class="p-1" for="specialty"><strong>Specialty</strong></label>
                                <select value="GENERAL" name="specialty" id="specialty" onChange={(e) => setSpecialty(e.target.value)}>
                                    <option value="GENERAL">General</option>
                                    <option value="PEDIATRIC">Pediatric</option>
                                    <option value="LAB">Lab</option>
                                    <option value="CANCER">Cancer</option>
                                    <option value="PRENATAL">Prenatal</option>
                                    <option value="OTHER">Other</option>
                                </select>
                            </div>
                            <div>
                                <label class="p-1" for="gender"><strong>Gender</strong></label>
                                <select value="UNKNOWN" name="gender" id="gender" onChange={(e) => setGender(e.target.value)}>
                                    <option value="MALE">Male</option>
                                    <option value="FEMALE">Female</option>
                                    <option value="UNKNOWN">Unknown</option>
                                </select>
                            </div>
                            <div>
                                <label class="p-1" for="age"><strong>Age</strong></label>
                                <input
                                    type="number"
                                    name="age"
                                    id="age"
                                    class="w-24"
                                    min={0}
                                    max={120}
                                    value={1}
                                    onChange={(e) => setAge(e.target.valueAsNumber)}/>
                            </div>
                            <div>
                                <label class="p-1" for="indication"><strong>Indication</strong></label>
                                <input type="text" name="indication" id="indication" onChange={(e) => setIndication(e.target.value)}/>
                            </div>
                        </div>
                    </div>
                    <div class='col-span-3 p-2'>
                        <label for="summary"><strong>Summary</strong></label> <br/>
                        <textarea name="summary" id="summary" onChange={(e) => setSummary(e.target.value)}></textarea>
                    </div>
                    <div class='col-span-2'>
                        <div class="flex flex-col">
                            <div>
                                <label for="testNames"><strong>Test Names</strong></label> <br/>
                                <textarea name="testNames" id="testNames" onChange={(e) => setTestNames(e.target.value)}></textarea>
                            </div>
                            <div>
                                <label for="testResults"><strong>Test Results</strong></label> <br/>
                                <textarea name="testResults" id="testResults" onChange={(e) => setTestResults(e.target.value)}></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="flex justify-center gap-2 mb-2">
                <button
                    class="bg-green-500 hover:bg-green-600 text-white py-2 px-4 rounded-lg active:bg-green-700 w-1/4"
                    onClick={createPatient}
                    >Submit</button>
                <button
                    class="bg-red-500 hover:bg-red-600 text-white py-2 px-4 rounded-lg active:bg-red-700 w-1/4"
                    onClick={props.cancel_callback}
                    >Cancel</button>
            </div>
        </div>
        </>
    );
}