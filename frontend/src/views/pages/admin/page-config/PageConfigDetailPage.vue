<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import PageConfigDetailComponent from "@/components/admin/page-config/PageConfigDetailComponent.vue";

export default defineComponent({
    name: 'PageConfigPage',
    components: {PageConfigDetailComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <page-config-detail-component/>
    </admin-page>
</template>