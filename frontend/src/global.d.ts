/// <reference types="@sveltejs/kit" />

interface Todo {
    id: number
    owner: string,
    content: string,
    completed: boolean
}