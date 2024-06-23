<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import VoucherDetailComponent from "@/components/admin/voucher/VoucherDetailComponent.vue";

export default defineComponent({
    name: 'UserPage',
    components: {VoucherDetailComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <voucher-detail-component/>
    </admin-page>
</template>