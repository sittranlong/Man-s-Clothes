<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import SizeDetailComponent from "@/components/admin/size/SizeDetailComponent.vue";

export default defineComponent({
    name: 'SizeDetailPage',
    components: {SizeDetailComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <size-detail-component/>
    </admin-page>
</template>