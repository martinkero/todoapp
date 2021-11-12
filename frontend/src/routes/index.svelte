<script lang="ts">
	import { onMount } from 'svelte';

	onMount(async () => {
		const response = await fetch('http://localhost:3000/api/uuid');
		const uuid = await response.text();
		window.location.href += uuid;
	});
</script>
