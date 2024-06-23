<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import TourComponent from "@/components/admin/tour/TourComponent.vue";

export default defineComponent({
    name: 'CategoryPage',
    components: {TourComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <tour-component/>
    </admin-page>
</template>