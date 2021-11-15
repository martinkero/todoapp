<script lang="ts">
	import { getUUIDRequest } from '$lib/Api.svelte';
	import { onMount } from 'svelte';

	onMount(async () => {
		const uuid = await getUUIDRequest();
		window.location.href += uuid;
	});
</script>
