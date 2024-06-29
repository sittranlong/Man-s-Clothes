<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import ColorDetailComponent from "@/components/admin/color/ColorDetailComponent.vue";

export default defineComponent({
    name: 'ColorDetailPage',
    components: {ColorDetailComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <color-detail-component/>
    </admin-page>
</template>