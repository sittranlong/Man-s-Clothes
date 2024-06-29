<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import ColorComponent from "@/components/admin/color/ColorComponent.vue";

export default defineComponent({
    name: 'ColorPage',
    components: {ColorComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <color-component/>
    </admin-page>
</template>