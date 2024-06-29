<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import SizeComponent from "@/components/admin/size/SizeComponent.vue";

export default defineComponent({
    name: 'SizePage',
    components: {SizeComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <size-component/>
    </admin-page>
</template>