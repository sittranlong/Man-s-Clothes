<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import BrandComponent from "@/components/admin/brand/BrandComponent.vue";

export default defineComponent({
    name: 'BrandPage',
    components: {BrandComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <brand-component/>
    </admin-page>
</template>