<script lang="ts">
import {defineComponent, inject} from 'vue'
import {toast} from "vue3-toastify";
import {VoucherService} from "@/base/service/voucher.service";
import {Voucher} from "@/base/model/voucher.model";
import formatMoney, {formatDate} from "@/plugins/utils";
import {useRouter} from "vue-router";

export default defineComponent({
    name: 'VoucherComponent',
    setup() {
        const headers = [
            {title: 'STT', value: 'index'},
            {title: 'Mã voucher', value: 'code'},
            {title: 'Hạn sử dụng', value: 'expiryDate'},
            {title: 'Số lượng', value: 'maxUsage', align: 'center'},
            {title: 'Loại', value: 'type'},
            {title: 'Hành động', value: 'action', sortable: false},
        ];
        return {
            headers,
            voucherService: inject('voucherService') as VoucherService,
            router: useRouter()
        }
    },
    data() {
        return {
            voucherPage: {
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
            this.voucherService.findBySearch(this.searchParams).then((data) => {
                this.voucherPage = data;
            }).finally(() => {
                this.isLoading = false;
            });
        },
        viewDetail(item: any) {
            this.router.push('/admin/voucher/' + item.id);
        },
        deleteVoucher(item: any) {
            if (item) {
                if (confirm('Bạn có chắc chắn muốn xóa danh mục này ?')) {
                    this.voucherService.delete(item?.id).then(() => {
                        this.search();
                        toast.success('Xóa danh mục thành công');
                    });
                }
            }
        },
        formatMoney,
        redirectNew() {
            this.router.push('/admin/voucher/new');
        },
        calculateDiscount(voucher: Voucher): string {
            if (voucher.type === Voucher.DISCOUNT_TYPE_PERCENT) {
                return `${voucher.discount}%`;
            } else {
                return formatMoney(voucher.discount);
            }
        }, formatDate
    },
    created() {
        this.search();
    }

})
</script>
<template>
    <div class="text-dark">
        <h1>Danh sách danh mục</h1>
        <v-text-field
            v-model="searchParams.keyword"
            label="Search"
            prepend-inner-icon="mdi-magnify"
            variant="outlined"
            hide-details
            @change="search"
            single-line
        ></v-text-field>
        <v-btn color="primary" @click="redirectNew">Thêm mới</v-btn>
        <v-data-table
            :headers="headers"
            :items="voucherPage.content"
            :loading="isLoading"
            :items-per-page="searchParams.size"
            :on-update:items-per-page="search"
            :page="searchParams.page"
            :on-update:page="search"
            :search="searchParams.keyword"
            :total-items="voucherPage.totalElements">
            <template v-slot:[`item.index`]="{index}">
                <td>{{ index + 1 }}</td>
            </template>
            <template v-slot:[`item.discount`]="{item}">
                {{ calculateDiscount(item) }}
            </template>
            <template v-slot:[`item.expiryDate`]="{item}">
                {{ formatDate(item.startDate, 'dd/MM/yyyy hh:MM:ss') }} -
                {{ formatDate(item.endDate, 'dd/MM/yyyy hh:MM:ss') }}
            </template>
            <template v-slot:[`item.action`]="{item}">
                <td>
                    <v-icon left @click="viewDetail(item)">mdi-eye</v-icon>
                    <v-icon left @click="deleteVoucher(item)">mdi-delete</v-icon>
                </td>
            </template>
        </v-data-table>
    </div>
</template>
<style scoped>
</style>