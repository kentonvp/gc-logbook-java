export default function ColorText(props: { color: string, text: string }) {
    return (
        <span class={"rounded-full p-1 " + props.color}>{props.text}</span>
    );
}