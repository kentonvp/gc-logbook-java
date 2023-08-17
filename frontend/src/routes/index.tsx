import { A } from "solid-start";

export default function Home() {
  return (
      <>
      <h1>Home</h1>

      <div class="bg-purple-300 hover:bg-purple-400">
        <h2>GC Logbook</h2>
        <A class="px-4" href={ "/gc-logbook" }>GC Logbook</A>
      </div>

      <div class="bg-green-200 hover:bg-green-300">
        <h2>Budget</h2>
        <A class="px-4" href={ "/budgeter" }>Budgeter</A>
      </div>
      </>
  );
}
