<script lang="ts" context="module">
	const apiEndpoint = '/api';

	export const getUUIDRequest = async (): Promise<String> => {
		const response = await fetch(`${apiEndpoint}/uuid`);

		checkResponse(response);

		return response.text();
	};

	export const getTodosRequest = async (uuid: String): Promise<Todo[]> => {
		const response = await fetch(`${apiEndpoint}/${uuid}`);

		checkResponse(response);

		return response.json();
	};

	export const createTodoRequest = async (text: string, uuid: String) => {
		const response = await fetch(`${apiEndpoint}/${uuid}`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				content: text
			} as Todo)
		});

		checkResponse(response);
	};

	export const deleteTodoRequest = async (todo: Todo, uuid: String) => {
		const response = await fetch(`${apiEndpoint}/${uuid}/${todo.id}`, {
			method: 'DELETE'
		});

		checkResponse(response);
	};

	export const updateTodoRequest = async (todo: Todo, uuid: String) => {
		const response = await fetch(`${apiEndpoint}/${uuid}/${todo.id}`, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(todo)
		});

		checkResponse(response);
	};

	const checkResponse = (response: Response) => {
		if (!response.ok) throw new Error();
	};
</script>
