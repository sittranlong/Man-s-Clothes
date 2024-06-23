<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import ChartComponent from "@/components/admin/chart/ChartComponent.vue";

export default defineComponent({
    name: 'OrderPage',
    components: {ChartComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <chart-component/>
    </admin-page>
</template>