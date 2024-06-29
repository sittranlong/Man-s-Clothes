<script lang="ts">
import {defineComponent, inject} from 'vue'
import {toast} from "vue3-toastify";
import {SizeService} from "@/base/service/size.service";
import {useRouter} from "vue-router";

export default defineComponent({
    name: 'SizeComponent',
    setup() {
        const headers = [
            {title: 'STT', value: 'index'},
            {title: 'Kích cỡ', value: 'name'},
            {title: 'Hành động', value: 'action'}
        ];
        return {
            headers,
            sizeService: inject('sizeService') as SizeService,
            router : useRouter()
        }
    },
    data() {
        return {
            sizePage: {
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
            this.sizeService.findBySearch(this.searchParams).then((data) => {
                this.sizePage = data;
            }).finally(() => {
                this.isLoading = false;
            });
        },
        viewDetail(item: any) {
            this.router.push('/admin/size/' + item.id);
        },
        deleteSize(item: any) {
            if (item) {
                if (confirm('Bạn có chắc chắn muốn xóa kích cỡ này ?')) {
                    this.sizeService.delete(item?.id).then(() => {
                        this.search();
                        toast.success('Xóa kích cỡ thành công');
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
        <h1>Danh sách kích cỡ</h1>
        <v-text-field
            v-model="searchParams.keyword"
            label="Search"
            prepend-inner-icon="mdi-magnify"
            variant="outlined"
            hide-details
            @change="search"
            single-line
        ></v-text-field>
        <v-btn color="primary" @click="() => router.push('/admin/size/new')">Thêm mới</v-btn>
        <v-data-table
            :headers="headers"
            :items="sizePage.content"
            :loading="isLoading"
            :items-per-page="searchParams.size"
            :on-update:items-per-page="search"
            :page="searchParams.page"
            :on-update:page="search"
            :search="searchParams.keyword"
            :total-items="sizePage.totalElements">
            <template v-slot:[`item.index`]="{index}">
                <td>{{index + 1}}</td>
            </template>
            <template v-slot:[`item.action`]="{item}">
                <td>
                    <v-icon left @click="viewDetail(item)">mdi-eye</v-icon>
                    <v-icon left @click="deleteSize(item)">mdi-delete</v-icon>
                </td>
            </template>
        </v-data-table>
    </div>
</template>
<style scoped>
</style>