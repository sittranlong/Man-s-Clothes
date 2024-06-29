<script lang="ts">
import {defineComponent, inject} from 'vue'
import {toast} from "vue3-toastify";
import {BrandService} from "@/base/service/brand.service";
import {useRouter} from "vue-router";

export default defineComponent({
    name: 'BrandComponent',
    setup() {
        const headers = [
            {title: 'STT', value: 'index'},
            {title: 'Tên thương hiệu', value: 'name'},
            {title: 'Hành động', value: 'action'}
        ];
        return {
            headers,
            brandService: inject('brandService') as BrandService,
            router : useRouter()
        }
    },
    data() {
        return {
            brandPage: {
                content: [],
                totalPages: 0,
                totalElements: 0,
                last: true,
                size: 0,
            } as any,
            searchParams: {
                page: 0,
                size: 10,
                keyword: '',
            },
            isLoading: false,
        }
    },
    methods: {
        async search() {
            this.isLoading = true;
            this.brandService.findBySearch(this.searchParams).then((data) => {
                this.brandPage = data;
            }).finally(() => {
                this.isLoading = false;
            });
        },
        viewDetail(item: any) {
            this.router.push('/admin/brand/' + item.id);
        },
        deleteBrand(item: any) {
            if (item) {
                if (confirm('Bạn có chắc chắn muốn xóa danh mục này ?')) {
                    this.brandService.delete(item?.id).then(() => {
                        this.search();
                        toast.success('Xóa danh mục thành công');
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
        <h1>Danh sách thương hiệu</h1>
        <v-text-field
            v-model="searchParams.keyword"
            label="Search"
            prepend-inner-icon="mdi-magnify"
            variant="outlined"
            hide-details
            @change="search"
            single-line
        ></v-text-field>
        <v-btn color="primary" @click="() => router.push('/admin/brand/new')">Thêm mới</v-btn>
        <v-data-table
            :headers="headers"
            :items="brandPage.content"
            :loading="isLoading"
            :items-per-page="searchParams.size"
            :on-update:items-per-page="search"
            :page="searchParams.page"
            :on-update:page="search"
            :search="searchParams.keyword"
            :total-items="brandPage.totalElements">
            <template v-slot:[`item.index`]="{index}">
                <td>{{index + 1}}</td>
            </template>
            <template v-slot:[`item.action`]="{item}">
                <td>
                    <v-icon left @click="viewDetail(item)">mdi-eye</v-icon>
                    <v-icon left @click="deleteBrand(item)">mdi-delete</v-icon>
                </td>
            </template>
        </v-data-table>
    </div>
</template>
<style scoped>
</style>