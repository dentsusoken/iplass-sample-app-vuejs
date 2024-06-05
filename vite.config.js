import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src/main/vue', import.meta.url))
    }
  },
  build: {
    rollupOptions: {
      output: {
        dir: path.resolve('./src/main/webapp/scripts'),
        entryFileNames: 'build.js',
        format: 'iife',
      }
    }
  }
})
