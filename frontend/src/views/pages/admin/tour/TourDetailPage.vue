<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import TourDetailComponent from "@/components/admin/tour/TourDetailComponent.vue";

export default defineComponent({
    name: 'CategoryPage',
    components: {TourDetailComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <tour-detail-component/>
    </admin-page>
</template>