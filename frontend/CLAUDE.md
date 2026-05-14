# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
npm install        # Install dependencies
npm run dev        # Dev server on port 8081
npm run build      # Production build
npm run lint       # oxlint + ESLint (both with --fix)
npm run format     # Prettier format on src/
```

## Stack

Vue 3 (Options API) · Vite 8 · Vue Router 5 · Pinia 3 · Bootstrap 5 · Axios · Phosphor Icons (`@phosphor-icons/vue`)

## Architecture

- `@` alias resolves to `src/`
- All `/api` requests are proxied by Vite to `http://localhost:8080` (backend)
- Routes defined in `src/router/index.js` — use lazy imports (`() => import(...)`) for non-home views
- Pinia stores in `src/stores/` use Composition API style (`defineStore` with a setup function returning refs/computeds)

## Key Conventions

- Use Options API in all components, not Composition API (`<script setup>`)
- API calls go through Axios with the `/api` prefix
- Linting runs oxlint first, then ESLint — fix lint errors before formatting