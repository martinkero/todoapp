<script lang="ts">
	import { getUUID } from '$lib/Utils.svelte';

	$: todos = [];
	const unicodeCrossMark = '&#10060;&#xfe0e;';

	export const refreshTodos = async () => {
		const res = await fetch(`http://localhost:3000/api/${getUUID()}`);

		if (!res.ok) throw new Error();

		todos = await res.json();
	};

	let promise = refreshTodos();

	const deleteTodo = async (todo: Todo) => {
		const res = await fetch(`http://localhost:3000/api/${getUUID()}/${todo.id}`, {
			method: 'DELETE'
		});

		refreshTodos();
	};

	const updateTodo = async (todo: Todo) => {
		const res = await fetch(`http://localhost:3000/api/${getUUID()}/${todo.id}`, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(todo)
		});

		refreshTodos();
	};
</script>

<section>
	{#await promise}
		<article>
			<strong>Loading todos..</strong>
		</article>
	{:then number}
		{#each todos as todo}
			<article>
				<input type="checkbox" bind:checked={todo.completed} on:change={() => updateTodo(todo)} />

				<strong>{todo.content}</strong>

				<!-- Unicode Cross mark-->
				<button on:click={() => deleteTodo(todo)}>{@html unicodeCrossMark}</button>
			</article>
		{/each}
	{:catch error}
		<article>
			<strong class="error">Error loading todos!</strong>
		</article>
	{/await}
</section>

<style>
	section {
		display: flex;
		flex-direction: column;
		background-color: var(--color-gray);
	}
	article {
		padding: 1.5rem 1rem;
		display: flex;
		align-items: center;
		border-top: 1px solid #fff;
	}
	strong {
		outline: none;
		width: 100%;
		margin: 0 1.5rem;
		font-size: 1.1rem;
	}
	button {
		padding: 0.1rem;
		background-color: rgb(0, 0, 0, 0);
		border: none;
		font-size: 1.2rem;
		color: var(--color-pink);
	}
	strong.error {
		color: var(--color-pink-dark);
	}
	input[type='checkbox'] {
		display: flex;
		min-height: 1.5rem;
		min-width: 1.5rem;
		border: 2px solid var(--color-pink);
		appearance: none;
		cursor: pointer;
		justify-content: center;
		align-items: center;
	}
	input[type='checkbox']:before {
		content: '';
		display: block;
		width: 0.5rem;
		height: 0.8rem;
		margin-bottom: 0.3rem;
		border-style: solid;
		border-color: #fff;
		border-width: 0 2px 2px 0;
		transform: rotate(45deg);
		opacity: 0;
	}
	input[type='checkbox']:checked {
		background: var(--color-pink);
	}
	input[type='checkbox']:checked::before {
		opacity: 1;
	}
</style>
