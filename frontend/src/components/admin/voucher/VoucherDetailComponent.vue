<script lang="ts">
import {defineComponent, inject} from 'vue'
import {toast} from "vue3-toastify";
import {Voucher, VOUCHER_TYPE} from "@/base/model/voucher.model";
import type {VoucherService} from "@/base/service/voucher.service";

export default defineComponent({
    name: 'VoucherDetailComponent',
    setup() {
        return {
            VOUCHER_TYPE_LIST: VOUCHER_TYPE,
            voucherService: inject('voucherService') as VoucherService
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
            this.$router.push('/admin/voucher');
        }
    },
    created() {
        this.id = this.$route.params.id as string;
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
                <v-text-field v-model="voucher.code" label="Mã voucher"></v-text-field>
                <v-select v-model="voucher.discountType" :items="VOUCHER_TYPE_LIST" label="Loại voucher"
                          item-title="text"
                          item-value="value">

                </v-select>
                <v-text-field v-model="voucher.discountValue" label="Giảm giá"></v-text-field>
                <v-text-field type="datetime-local" v-model="voucher.startDate" label="Ngày bắt đầu"></v-text-field>
                <v-text-field type="datetime-local" v-model="voucher.endDate" label="Ngày kết thúc"></v-text-field>
                <v-text-field v-model="voucher.quantity" label="Số lượng"></v-text-field>
                <div class="row">
                    <v-btn class="col" color="secondary" @click="back">Quay lại</v-btn>
                    <v-btn class="col" color="primary" @click="save">Lưu</v-btn>
                </div>
            </v-form>
        </div>

    </div>
</template>