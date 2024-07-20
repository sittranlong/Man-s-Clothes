<template>
    <header-component/>
    <div class="container">
        <v-stepper :model-value="activeStep" :mobile="false">
            <v-stepper-header>
                <v-stepper-item
                    :title="step.title"
                    :value="step.step"
                    :complete="step.completed"
                    :editable="step.step === activeStep"
                    :complete-icon="step.iconComplete"
                    :error="step.error"
                    :subtitle="step.subtitle"
                    :color="step.completed ? 'green' : ''"
                    v-for="step in steps"
                    :key="step.step"
                >
                </v-stepper-item>
            </v-stepper-header>
            <div v-if="activeStep === 1">
                <v-card class="w-100">
                    <v-card-title>
                        <h3 class="text-center">Thông tin giao hàng</h3>
                    </v-card-title>
                    <v-card-text>
                        <v-form>
                            <v-row>
                                <!--                            Sử dụng địa chỉ mặc định-->
                                <v-checkbox @change.prevent="getDefaultContact($event)" label="Sử dụng địa chỉ mặc định"
                                            class="mb-2"></v-checkbox>
                            </v-row>
                            <v-row>
                                <v-col cols="12" md="6">
                                    <v-text-field v-model="orderRequest.name" label="Họ tên (*)"
                                                  :rules="[v => !!v || 'Họ tên không được để trống']"
                                                  required></v-text-field>
                                </v-col>
                                <v-col cols="12" md="6">
                                    <v-text-field v-model="orderRequest.address" label="Địa chỉ (*)"
                                                  :rules="[v => !!v || 'Địa chỉ không được để trống']"
                                                  required></v-text-field>
                                </v-col>
                                <v-col cols="12" md="6">
                                    <v-text-field v-model="orderRequest.phone" label="Số điện thoại (*)"
                                                  :rules="[v => !!v || 'Số điện thoại không được để trống']"
                                                  required></v-text-field>
                                </v-col>
                            </v-row>
                            <v-divider></v-divider>
                        </v-form>
                    </v-card-text>
                </v-card>
            </div>

            <div v-if="activeStep === 2">
                <v-card>
                    <v-card-text>
                        <v-radio-group v-model="orderRequest.paymentMethod" label="Hình thức thanh toán">
                            <div v-if="isAdmin">
                                <v-radio v-for="method in [PAYMENT_METHOD.QRCODE,PAYMENT_METHOD.COD, PAYMENT_METHOD.METHOD_AT_COUNTER]" :key="method"
                                         :label="method.text" :value="method.value">
                                </v-radio>
                            </div>
                            <div v-else>
                                <v-radio v-for="method in [PAYMENT_METHOD.QRCODE,PAYMENT_METHOD.COD]" :key="method"
                                         :label="method.text" :value="method.value">
                                </v-radio>
                            </div>
                        </v-radio-group>
                        <v-divider></v-divider>
                        <!--                        mã voucher-->
                        <v-text-field class="w-25" v-model="orderRequest.voucherCode"
                                      label="Mã giảm giá(nếu có)"></v-text-field>
                        <!--                        dialog QR-->

                    </v-card-text>
                </v-card>
            </div>

            <div v-if="activeStep === 3">
                <v-card>
                    <v-card-title>
                        <h3 class="text-center">Hoàn tất</h3>
                    </v-card-title>
                    <v-card-text>
                        <v-row>
                            <v-col cols="12" md="6">
                                <v-text-field v-model="orderRequest.name" label="Họ tên" required></v-text-field>
                            </v-col>
                            <v-col cols="12" md="6">
                                <v-text-field v-model="orderRequest.address" label="Địa chỉ" required></v-text-field>
                            </v-col>
                            <v-col cols="12" md="6">
                                <v-text-field v-model="orderRequest.phone" label="Số điện thoại"
                                              required></v-text-field>
                            </v-col>
                        </v-row>
                        <v-divider></v-divider>
                        <v-radio-group v-model="orderRequest.paymentMethod" label="Hình thức thanh toán">
                            <div v-if="isAdmin">
                                <v-radio v-for="method in [PAYMENT_METHOD.QRCODE,PAYMENT_METHOD.COD, PAYMENT_METHOD.METHOD_AT_COUNTER]" :key="method"
                                         :label="method.text" :value="method.value">
                                </v-radio>
                            </div>
                            <div v-else>
                                <v-radio v-for="method in [PAYMENT_METHOD.QRCODE,PAYMENT_METHOD.COD]" :key="method"
                                         :label="method.text" :value="method.value">
                                </v-radio>
                            </div>
                        </v-radio-group>
                        <v-divider></v-divider>
                        <v-divider></v-divider>
                        <v-text-field class="w-25" v-model="orderRequest.voucherCode"
                                      label="Mã giảm giá"></v-text-field>
                        <v-divider></v-divider>
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>STT</th>
                                <th>Tên sản phẩm</th>
                                <th>Số lượng</th>
                                <th>Giá</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="(item,index) in orderInfo?.cartList" :key="index">
                                <td>{{ index + 1 }}</td>
                                <td>{{ item.productDetail?.product?.name }}</td>
                                <td>{{ item.quantity }}</td>
                                <td>{{ formatMoney(item.price) }}</td>
                            </tr>
                            <tr>
                                <td colspan="3">Tổng tiền sản phẩm (1)</td>
                                <td class="font-weight-bold">{{ formatMoney(orderInfo?.total) }}</td>
                            </tr>
                            <tr>
                                <td colspan="3">Phí vận chuyển (2)</td>
                                <td class="font-weight-bold">{{ formatMoney(orderInfo.shippingFee) }}</td>
                            </tr>
                            <tr>
                                <td colspan="3">Giảm giá (3)</td>
                                <td class="font-weight-bold">{{ formatMoney(orderInfo.voucher?.discountValue) }}</td>
                            </tr>
                            <tr>
                                <td colspan="3">Thành tiền (4 = 1 + 2 - 3)</td>
                                <td class="font-weight-bold">{{ formatMoney(orderInfo?.finalTotal) }}</td>
                            </tr>
                            <tr>
                                <td colspan="4" v-if="orderRequest.paymentMethod === PAYMENT_METHOD.QRCODE.value">
                                    <v-img :src="orderInfo?.qrCode?.data?.qrDataURL" alt="QR Code" width="250"
                                           height="250"></v-img>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <v-divider></v-divider>
                        <v-btn type="submit" @click.prevent="createOrder">Xác nhận thanh toán</v-btn>
                    </v-card-text>
                </v-card>
            </div>

            <v-stepper-actions prev-text="Quay lại" next-text="Tiếp" @click:next="next"
                               @click:prev="activeStep--">
            </v-stepper-actions>
        </v-stepper>
    </div>
    <footer-component/>
</template>

<script lang="ts">
import {defineComponent, inject} from 'vue'
import {Order, OrderRequest, PaymentMethod} from "@/base/model/order.model";
import {OrderService} from "@/base/service/order.service";
import {useRoute, useRouter} from "vue-router";
import {useStore} from "vuex";
import HeaderComponent from "@/components/header/HeaderComponent.vue";
import FooterComponent from "@/components/footer/FooterComponent.vue";
import formatMoney from "@/plugins/utils";
import {toast} from "vue3-toastify";

export default defineComponent({
    name: 'CheckOutPage',
    components: {FooterComponent, HeaderComponent},
    setup() {
        return {
            orderService: inject('orderService') as OrderService,
            steps: [
                {
                    step: 1,
                    title: 'Thông tin giao hàng',
                    icon: 'mdi-check',
                    completed: false as boolean,
                    error: false as boolean,
                    iconComplete: 'mdi-check',
                    subtitle: 'Nhập thông tin giao hàng'
                },
                {
                    step: 2,
                    title: 'Thanh toán',
                    icon: 'mdi-cash',
                    completed: false as boolean,
                    error: false as boolean,
                    iconComplete: 'mdi-check',
                    subtitle: 'Chọn hình thức thanh toán',
                },
                {
                    step: 3,
                    title: 'Hoàn tất',
                    icon: 'mdi-check',
                    completed: false as boolean,
                    error: false as boolean,
                    iconComplete: 'mdi-check',
                    subtitle: 'Xác nhận đơn hàng'
                }
            ],
            route: useRoute(),
            router: useRouter(),
            store: useStore()
        }
    },
    data() {
        return {
            orderRequest: {} as OrderRequest,
            order: {} as Order,
            orderInfo: {} as any,
            voucherCode: 0,
            price: 0,
            feeShip: 0,
            qrCode: '',
            isShowQRCode: false,
            activeStep: 1,
            PAYMENT_METHOD: PaymentMethod,
            isAdmin: false
        }
    },
    methods: {
        formatMoney,
        next() {
            this.activeStep++
            if (this.activeStep === 2) {
                this.steps[0].completed = !!this.orderRequest.name && !!this.orderRequest.address && !!this.orderRequest.phone;
                if (!this.steps[0].completed) {
                    this.steps[0].error = true;
                    this.activeStep = 1;
                } else {
                    this.steps[0].error = false;
                }
            } else if (this.activeStep === 3) {
                this.steps[1].completed = !!this.orderRequest.paymentMethod;
                this.getOrderInfo();
                if (!this.steps[1].completed) {
                    this.steps[1].error = true;
                    this.activeStep = 2;
                } else {
                    this.steps[1].error = false;
                }
            }
            return true;
        },
        createOrder() {
            if (!this.orderRequest.paymentMethod) {
                toast.error('Vui lòng chọn hình thức thanh toán');
                return;
            }
            if (this.orderRequest.paymentMethod === PaymentMethod.QRCODE.value) {
                this.isShowQRCode = true;
            }
            this.orderService.createOrder(this.orderRequest).then((res) => {
                localStorage.setItem('orderId', res.order?.id);
                toast.success('Đặt hàng thành công');
            }, (error) => {
                toast.error(error.response.data.message);
            });
        },
        getOrderInfo() {
            console.log(this.orderRequest)
            this.orderService.getOrderInfo(this.orderRequest).then((res) => {
                this.orderInfo = res;
            }, (error) => {
                toast.error(error.response.data.message);
            });
        },
        init() {
            let idString = this.route.query?.cartIdList as string;
            this.orderRequest.cartIdList = idString?.split(',').map(String);
        },
        getDefaultContact($event: any) {
            if ($event.target.checked) {
                this.orderService.getDefaultContact().then((res) => {
                    this.orderRequest.phone = res?.phone;
                    this.orderRequest.address = res?.address;
                    this.orderRequest.name = res?.fullName;
                });
            }
        }
    },
    created() {
        this.isAdmin = this.store.getters.isAdmin;
        this.init();
    }
})
</script>

<style scoped>
</style>
