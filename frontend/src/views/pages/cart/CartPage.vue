<script lang="ts">
import {defineComponent} from "vue";
import HeaderComponent from "@/components/header/HeaderComponent.vue";
import FooterComponent from "@/components/footer/FooterComponent.vue";

export default defineComponent({
    name: 'CartPage',
    components: {
        HeaderComponent,
        FooterComponent
    },
    data() {
        return {}
    },
    methods: {}
});
</script>

<template>
    <div>
        <header-component/>
        <footer-component/>
    </div>
</template>

<style scoped>
@import "style.css";
</style>