<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import VoucherComponent from "@/components/admin/voucher/VoucherComponent.vue";

export default defineComponent({
    name: 'UserPage',
    components: {VoucherComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <voucher-component/>
    </admin-page>
</template>