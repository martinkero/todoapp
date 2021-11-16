<script lang="ts">
	import { createTodoRequest } from '$lib/Api.svelte';

	export let uuid: string;
	export let refreshTodos: () => Promise<void>;

	let text: string;
	let error = false;

	const createTodo = async () => {
		if (!text) return;

		error = false;

		try {
			await createTodoRequest(text, uuid);
		} catch (e) {
			error = true;
		}

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
		padding: 0.6rem;
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
	input:focus {
		outline: none;
	}
	input::placeholder {
		color: var(--color-gray);
	}
	.error::placeholder {
		color: var(--color-pink-dark);
	}
	button {
		border: none;
		background-color: var(--color-gray);
		font-size: 1.2rem;
		padding: 0.6rem;
	}
</style>
