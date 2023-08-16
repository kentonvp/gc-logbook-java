export default function ColorText(props: { color: string, text: string }) {
    return (
        <span class={props.color}>{props.text}</span>
    );
}