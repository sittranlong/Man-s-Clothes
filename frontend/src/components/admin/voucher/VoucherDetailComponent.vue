<script lang="ts">
import { defineComponent, inject } from 'vue';
import { toast } from "vue3-toastify";
import {Voucher, VOUCHER_STATUS_LIST, VOUCHER_TYPE, VOUCHER_TYPE_LIST} from "@/base/model/voucher.model";
import type { VoucherService } from "@/base/service/voucher.service";
import {useRoute, useRouter} from "vue-router";

export default defineComponent({
    name: 'VoucherDetailComponent',
    setup() {
        return {
            VOUCHER_TYPE_LIST: VOUCHER_TYPE_LIST,
            VOUCHER_TYPE: VOUCHER_TYPE,
            VOUCHER_STATUS_LIST: VOUCHER_STATUS_LIST,
            Voucher : Voucher,
            voucherService: inject('voucherService') as VoucherService,
            router : useRouter(),
            route : useRoute()
        }
    },
    data() {
        return {
            voucher: new Voucher(),
            id: ''
        }
    },
    methods: {
        async save() {
            if (this.voucher.id) {
                this.voucherService.update(this.voucher).then(() => {
                    toast.success('Cập nhật voucher thành công');
                });
            } else {
                this.voucherService.save(this.voucher).then(() => {
                    toast.success('Tạo mới voucher thành công');
                });
            }
        },
        async getVoucher(id: any) {
            this.voucherService.get(id).then((data) => {
                this.voucher = data;
            });
        },
        back() {
            this.router.push('/admin/voucher');
        }
    },
    created() {
        this.id = this.route.params.id as string;
        if (this.id) {
            this.getVoucher(this.id);
        }
    }
})
</script>

<template>
    <div class="text-dark text-center">
        <div>
            <h1>Chi tiết voucher</h1>
        </div>
        <div class="d-flex justify-content-center">
            <v-form class="container row col-6">
                <v-text-field v-model="voucher.code" label="Mã voucher(*)"></v-text-field>
                <v-text-field v-model="voucher.name" label="Tên voucher(*)"></v-text-field>
                <v-select v-model="voucher.type" :items="VOUCHER_TYPE_LIST" label="Loại voucher"
                          item-title="text"
                          item-value="value">
                </v-select>
                <v-text-field v-model="voucher.discount" label="Giảm giá(*)"></v-text-field>
                <v-text-field v-model="voucher.minTotal" label="Áp dụng cho đơn trên(*)"></v-text-field>
                <v-text-field v-if="voucher.type === VOUCHER_TYPE.DISCOUNT_TYPE_PERCENT.value" v-model="voucher.maxDiscount" label="Giảm giá tối đa"></v-text-field>
                <v-text-field v-model="voucher.maxUsage" label="Số lượng"></v-text-field>
                <v-text-field v-model="voucher.usage" label="Số lần sử dụng"></v-text-field>
                <v-text-field type="datetime-local" v-model="voucher.startDate" label="Ngày bắt đầu"></v-text-field>
                <v-text-field type="datetime-local" v-model="voucher.endDate" label="Ngày kết thúc"></v-text-field>
                <v-text-field v-model="voucher.description" label="Mô tả"></v-text-field>
               <v-select v-model="voucher.status" :items="VOUCHER_STATUS_LIST" label="Trạng thái"
                          item-title="text"
                          item-value="value">
                </v-select>
                <div class="row">
                    <v-btn class="col" color="secondary" @click="back">Quay lại</v-btn>
                    <v-btn class="col" color="primary" @click="save">Lưu</v-btn>
                </div>
            </v-form>
        </div>
    </div>
</template>