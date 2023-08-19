import { createSignal } from "solid-js";

export default function NewPatientForm(props: {successCallback: () => void, cancelCallback: () => void}) {

    const [specialty, setSpecialty] = createSignal('GENERAL');
    const [gender, setGender] = createSignal('UNKNOWN');
    const [age, setAge] = createSignal(1);
    const [pDate, setDate] = createSignal(new Date().toISOString());
    const [indication, setIndication] = createSignal('');
    const [summary, setSummary] = createSignal('');
    const [testNames, setTestNames] = createSignal('');
    const [testResults, setTestResults] = createSignal('');

    const resetForm = () => {
        setSpecialty('GENERAL');
        setGender('UNKNOWN');
        setAge(1);
        setDate(new Date().toISOString());
        setIndication('');
        setSummary('');
        setTestNames('');
        setTestResults('');
    };

    const createPatient = async () => {
        const patient = {
            specialty: specialty(),
            gender: gender(),
            age: age(),
            indication: indication(),
            date: pDate(),
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

        resetForm();
        props.successCallback()
    };

    return (
        <div class="relative flex flex-col justify-between bg-purple-400 shadow-lg rounded-lg h-full">
            <div>
                <h1 class="text-center p-5">New Patient Form</h1>
                <div class="h-full">
                    <div class="grid grid-cols-6 gap-1 p-1 h-full">
                        <div class='col-span-1 m-2'>
                            <div class='flex flex-col justify-center p-2 h-full'>
                                <div class="grid grid-rows-2">
                                    <label class="p-1" for="specialty"><strong>Specialty</strong></label>
                                    <select class="w-32" value={specialty()} name="specialty" id="specialty" onChange={(e) => setSpecialty(e.target.value)}>
                                        <option value="GENERAL">General</option>
                                        <option value="PEDIATRIC">Pediatric</option>
                                        <option value="LAB">Lab</option>
                                        <option value="CANCER">Cancer</option>
                                        <option value="PRENATAL">Prenatal</option>
                                        <option value="OTHER">Other</option>
                                    </select>
                                </div>
                                <div class="grid grid-rows-2">
                                    <label class="p-1" for="gender"><strong>Gender</strong></label>
                                    <select class="w-32" value={gender()} name="gender" id="gender" onChange={(e) => setGender(e.target.value)}>
                                        <option value="MALE">Male</option>
                                        <option value="FEMALE">Female</option>
                                        <option value="UNKNOWN">Unknown</option>
                                    </select>
                                </div>
                                <div class="grid grid-rows-2">
                                    <label class="p-1" for="age"><strong>Age</strong></label>
                                    <input
                                        type="number"
                                        name="age"
                                        id="age"
                                        class="w-32"
                                        min={0}
                                        max={120}
                                        value={age()}
                                        onChange={(e) => setAge(e.target.valueAsNumber)} />
                                </div>
                                <div class="grid grid-rows-2">
                                    <label class="p-1" for="date"><strong>Date</strong></label>
                                    <input
                                        type="date"
                                        name="date"
                                        id="date"
                                        value={pDate().slice(0, 10)}
                                        class="w-32"
                                        onChange={(e) => setDate(e.target.valueAsDate.toISOString())} />
                                </div>
                            </div>
                        </div>
                        <div class='col-span-3 flex flex-col justify-center m-2'>
                            <div class="grid grid-rows-2">
                                <label class="p-1" for="indication"><strong>Indication</strong></label>
                                <input class="w-full" type="text" name="indication" id="indication" value={indication()} maxLength={255} onInput={(e) => setIndication(e.target.value)} />
                                <p class="text-right">{indication().length}/255</p>
                            </div>
                            <div>
                                <label for="summary"><strong>Summary</strong></label>
                                <textarea class="w-full h-full" name="summary" id="summary" maxLength={1000} value={summary()} onInput={(e) => setSummary(e.target.value)}></textarea>
                                <p class="text-right">{summary().length}/1000</p>
                            </div>
                        </div>
                        <div class='col-span-2 m-2'>
                            <div class="flex flex-col justify-evenly h-full m-2">
                                <div>
                                    <label for="testNames"><strong>Test Names</strong></label> <br />
                                    <textarea class="w-full h-full" name="testNames" id="testNames" maxLength={255} value={testNames()} onInput={(e) => setTestNames(e.target.value)}></textarea>
                                    <p class="text-right">{testNames().length}/255</p>
                                </div>
                                <div>
                                    <label for="testResults"><strong>Test Results</strong></label> <br />
                                    <textarea class="w-full h-full" name="testResults" id="testResults" maxLength={255} value={testResults()} onInput={(e) => setTestResults(e.target.value)}></textarea>
                                    <p class="text-right">{testResults().length}/255</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="flex justify-center p-2">
                <button
                    class="bg-purple-500 hover:bg-purple-600 text-white py-2 px-4 rounded-lg active:bg-purple-700"
                    onClick={createPatient}
                    >Submit</button>
            </div>
            <button class='absolute top-0 right-0 px-2 hover:opacity-50' onClick={props.cancelCallback} title="Cancel">🅧</button>
        </div>
    );
}