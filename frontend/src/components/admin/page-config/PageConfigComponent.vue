<script lang="ts">
import {defineComponent, inject} from 'vue'
import {toast} from "vue3-toastify";
import {PageConfigService} from "@/base/service/page-config.service";
import {useRouter} from "vue-router";

export default defineComponent({
    name: 'PageConfigComponent',
    setup() {
        const headers = [
            {title: 'STT', value: 'index'},
            {title: 'Tên trang', value: 'name'},
            {title: 'Loại trang', value: 'type'},
            {title: 'Thao tác', value: 'action'},
        ];
        return {
            headers,
            pageConfigService: inject('pageConfigService') as PageConfigService,
            router: useRouter()
        }
    },
    data() {
        return {
            pageConfigPage: {
                content: [],
                totalPages: 0,
                totalElements: 0,
                last: true,
                size: 0,
            } as any,
            searchParams: {
                page: 1,
                size: 10,
                keyword: '',
            },
            isLoading: false,
        }
    },
    methods: {
        async search() {
            this.isLoading = true;
            this.pageConfigService.findBySearch(this.searchParams).then((data) => {
                this.pageConfigPage = data;
            }).finally(() => {
                this.isLoading = false;
            });
        },
        viewDetail(item: any) {
            this.router.push('/admin/page-config/' + item.id);
        },
        deletePageConfig(item: any) {
            if (item) {
                if (confirm('Bạn có chắc chắn muốn xóa cấu hình này ?')) {
                    this.pageConfigService.delete(item?.id).then(() => {
                        this.search();
                        toast.success('Xóa cấu hình thành công');
                    });
                }
            }
        }
    },
    created() {
        this.search();
    }

})
</script>
<template>
    <div class="text-dark">
        <h1>Danh sách cấu hình trang</h1>
        <v-text-field
            v-model="searchParams.keyword"
            label="Search"
            prepend-inner-icon="mdi-magnify"
            variant="outlined"
            hide-details
            @change="search"
            single-line
        ></v-text-field>
        <v-btn pageConfig="primary" @click="() => router.push('/admin/page-config/new')">Thêm mới</v-btn>
        <v-data-table-server
            :headers="headers"
            :items="pageConfigPage.content"
            :loading="isLoading"
            v-model:items-per-page="searchParams.size"
            v-model:page="searchParams.page"
            :items-length="pageConfigPage.totalElements"
            :items-per-page="searchParams.size"
            @update:itemsPerPage="search"
            @update:page="search"
            :search="searchParams.keyword">
            <template v-slot:[`item.index`]="{index}">
                <td>{{ index + 1 }}</td>
            </template>
            <template v-slot:[`item.action`]="{item}">
                <td>
                    <v-icon left @click="viewDetail(item)">mdi-eye</v-icon>
                    <v-icon left @click="deletePageConfig(item)">mdi-delete</v-icon>
                </td>
            </template>
        </v-data-table-server>
    </div>
</template>
<style scoped>
</style>