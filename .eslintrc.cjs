/* eslint-env node */
require('@rushstack/eslint-patch/modern-module-resolution')

module.exports = {
  root: true,
  extends: [
    'plugin:vue/vue3-recommended',
    'eslint:recommended',
    '@vue/eslint-config-prettier/skip-formatting'
  ],
  parserOptions: {
    ecmaVersion: 'latest'
  },
  rules: {
    'vue/multi-word-component-names': 'off',
    'vue/valid-template-root': 'off'
  },
  env: {
    browser: true,
    node: true,
    es2023: true
  },
  globals: {
    lang: 'readonly',
    tcPath: 'readonly'
  }
}
