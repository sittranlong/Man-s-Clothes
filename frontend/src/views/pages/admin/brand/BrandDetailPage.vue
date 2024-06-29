<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import BrandDetailComponent from "@/components/admin/brand/BrandDetailComponent.vue";

export default defineComponent({
    name: 'BrandDetailPage',
    components: {BrandDetailComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <brand-detail-component/>
    </admin-page>
</template>