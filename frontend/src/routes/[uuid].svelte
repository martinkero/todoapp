<script lang="ts">
	import TodoList from '$lib/todo/TodoList.svelte';
</script>

<section>
	<TodoList />
</section>

<svelte:head>
	<link href="https://fonts.googleapis.com/css?family=Open Sans" rel="stylesheet" />
</svelte:head>

<style>
	:global(:root) {
		--color-gray: #f2f2f2;
		--color-pink: #ff80b4;
		--color-pink-dark: #991a4e;
	}
	:global(*) {
		font-family: 'Open Sans';
	}
	:global(body) {
		margin: 0;
	}
	section {
		display: flex;
		justify-content: center;
		flex-wrap: wrap;
	}
	@media only screen and (min-width: 768px) {
		section {
			padding: 10rem 0;
		}
	}
</style>
