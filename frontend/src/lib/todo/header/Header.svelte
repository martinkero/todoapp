<script lang="ts">
	import { getUUID } from '$lib/Utils.svelte';

	let text: string;
	let error = false;

	export let refreshTodos: () => Promise<void>;

	const createTodo = async () => {
		if (!text) return;

		const res = await fetch(`http://localhost:3000/api/${getUUID()}`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				content: text
			} as Todo)
		});

		error = !res.ok;
		text = '';

		refreshTodos();
	};

	const handleKeypress = ({ key }) => {
		if (key === 'Enter') createTodo();
	};
</script>

<header>
	<input
		type="text"
		placeholder={error ? 'Error creating todo!' : 'What to do?'}
		class:error
		bind:value={text}
		on:keypress={handleKeypress}
	/>
	<button on:click={createTodo}>Add</button>
</header>

<style>
	header {
		background-color: var(--color-pink);
		padding: 0.5rem;
		display: flex;
		justify-content: space-between;
	}
	input {
		border: none;
		background-color: var(--color-pink);
		font-size: 1.5rem;
		width: 100%;
		padding: 0 0.5rem;
	}
	input::placeholder {
		color: var(--color-gray);
	}
	input:focus {
		outline: none;
	}
	button {
		border: none;
		background-color: var(--color-gray);
		font-size: 1.2rem;
		padding: 0.5rem;
	}
	.error::placeholder {
		color: var(--color-pink-dark);
	}
</style>
