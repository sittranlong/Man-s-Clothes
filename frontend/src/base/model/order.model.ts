import {User} from "@/base/model/user.model";
import {Voucher} from "@/base/model/voucher.model";
import {ProductDetail} from "@/base/model/product.model";

export class Order {
    public static readonly STATUS_PENDING = "PENDING";
    public static readonly STATUS_PROCESSING = "PROCESSING";
    public static readonly STATUS_COMPLETED = "COMPLETED";
    public static readonly STATUS_CANCELLED = "CANCELLED";
    public static readonly STATUS_REFUNDED = "REFUNDED";
    public static readonly STATUS_SHIPPING = "SHIPPING";
    id?: string;
    createDate?: number;
    code?: string;
    total: number;
    status?: string;
    user: User;
    voucher?: Voucher;
    address: string;
    phone: string;
    note?: string;
    paymentMethod: number
    discount?: number;
    shippingFee: number;
    finalTotal: number;
    orderDetails: OrderDetail[];
}

export class OrderDetail {
    id?: string;
    price: number;
    quantity: number;
    total: number;
    status?: string;
    order: Order;
    productDetail: ProductDetail;
}

export interface OrderRequest {
    orderId: string;
    voucherCode: string;
    reason: string;
    address: string;
    phone: string;
    note: string;
    paymentMethod: number;
    cartIdList: string[];
    name: string;
}


export const OrderStatus = [
    {value: Order.STATUS_PENDING, text: 'Chờ xác nhận'},
    {value: Order.STATUS_PROCESSING, text: 'Đang xử lý'},
    {value: Order.STATUS_COMPLETED, text: 'Hoàn thành'},
    {value: Order.STATUS_CANCELLED, text: 'Đã hủy'},
    {value: Order.STATUS_REFUNDED, text: 'Đã hoàn tiền'},
    {value: Order.STATUS_SHIPPING, text: 'Đang giao hàng'},
];

export const PaymentMethod = {
    COD: {value: 1, text: 'Thanh toán khi nhận hàng'},
    QRCODE: {value: 2, text: 'Thanh toán qua mã QR'},
    METHOD_AT_COUNTER: {value: 3, text: 'Thanh toán tại quầy'},
};

export const OrderStatusText = (status: string | undefined) => {
    if (status) {
        const orderStatus = OrderStatus.find(item => item.value === status);
        return orderStatus ? orderStatus.text : '';
    }
    return '';
};