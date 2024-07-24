<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import PageConfigComponent from "@/components/admin/page-config/PageConfigComponent.vue";

export default defineComponent({
    name: 'PageConfigPage',
    components: {PageConfigComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <page-config-component/>
    </admin-page>
</template>