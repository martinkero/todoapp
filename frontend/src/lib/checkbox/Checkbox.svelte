<script lang="ts">
	export let checked: boolean;
	export let onChange: () => void;
</script>

<input type="checkbox" bind:checked on:change={onChange} />

<style>
	input[type='checkbox'] {
		display: flex;
		min-height: 1.5rem;
		min-width: 1.5rem;
		border: 2px solid var(--color-pink);
		appearance: none;
		cursor: pointer;
		justify-content: center;
		transition: background-color 0.1s ease-in-out;
	}
	input[type='checkbox']:before {
		content: '';
		width: 0.5rem;
		height: 0.8rem;
		border-style: solid;
		border-color: #fff;
		border-width: 0 2px 2px 0;
		transform: rotate(45deg);
		opacity: 0;
		transition: opacity 0.1s ease-in-out;
	}
	input[type='checkbox']:checked {
		background-color: var(--color-pink);
	}
	input[type='checkbox']:checked::before {
		opacity: 1;
	}
</style>
