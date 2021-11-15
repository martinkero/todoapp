<script lang="ts">
	import { getTodosRequest, updateTodoRequest, deleteTodoRequest } from '$lib/Api.svelte';
	import Checkbox from '$lib/checkbox/Checkbox.svelte';

	export let uuid;

	const UNICODE_CROSS_MARK = '&#10060;&#xfe0e;';

	$: todos = [];

	export const refreshTodos = async () => {
		todos = await getTodosRequest(uuid);
	};

	const deleteTodo = async (todo: Todo) => {
		await deleteTodoRequest(todo, uuid);
		refreshTodos();
	};
	const updateTodo = async (todo: Todo) => {
		await updateTodoRequest(todo, uuid);
		refreshTodos();
	};
</script>

<section>
	{#await refreshTodos()}
		<article>
			<strong>Loading todos..</strong>
		</article>
	{:then number}
		{#each todos as todo}
			<article>
				<Checkbox bind:checked={todo.completed} onChange={() => updateTodo(todo)} />

				<strong class:completed={todo.completed}>{todo.content}</strong>

				<button on:click={() => deleteTodo(todo)}>{@html UNICODE_CROSS_MARK}</button>
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
		padding: 1rem;
		display: flex;
		align-items: baseline;
		border-top: 1px solid #fff;
	}
	strong {
		outline: none;
		width: 100%;
		margin: 0 1rem;
		font-size: 1.1rem;
		overflow-wrap: anywhere;
		white-space: pre-wrap;
	}
	strong.completed {
		text-decoration: line-through;
	}
	strong.error {
		color: var(--color-pink-dark);
	}
	button {
		padding: 0.1rem;
		background-color: rgb(0, 0, 0, 0);
		border: none;
		font-size: 1.2rem;
		color: var(--color-pink);
	}
</style>
