<script lang="ts">
	import Header from './header/Header.svelte';
	import List from './list/List.svelte';

	export let uuid;
	let refreshTodos: () => Promise<void>;
</script>

<article>
	<Header {uuid} {refreshTodos} />
	<List {uuid} bind:refreshTodos />
</article>

<style>
	article {
		width: 35rem;
	}
	:global(input[type='checkbox']:hover) {
		cursor: pointer;
	}
	:global(button:hover) {
		transform: translateY(-1px);
		cursor: pointer;
	}
	:global(button:active) {
		transform: translateY(0px);
	}
</style>
