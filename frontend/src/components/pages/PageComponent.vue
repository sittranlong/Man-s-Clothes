<script lang="tsx">
import {defineComponent, ref} from "vue";

export default defineComponent({
	name: 'PageComponent',
	props: {
		page: {
			type: Number,
			default: 0
		},
		totalPage: {
			type: Number,
			default: 0
		},
		pageChange: {
			type: Function,
			default: () => {
			}
		}
	},
	setup(props) {
		let page = ref(props.page);
		const totalPage = ref(props.totalPage);
		const pageChange = (page: number) => {
			props.pageChange(page);
		}
		return () => (
			<div>
				<ul class="pages">
					<li>
						<a role="button" onClick={() => pageChange(--page.value)}>
							<i class="fa fa-angle-double-left"></i>
						</a>
					</li>
					{Array.from({ length: totalPage.value }, (_, index) => index + 1).map(
						(pageNumber, index) => {
							if (
								(index < 5 && page.value < 5) ||
								(index >= page.value - 2 && index <= page.value + 2) ||
								(index > totalPage.value - 5)
							) {
								return (
									<li key={pageNumber} class={page.value === pageNumber ? 'active' : ''}>
										<a role="button" onClick={() => {page.value = pageNumber;pageChange(pageNumber)}}>
											{pageNumber}
										</a>
									</li>
								);
							} else if ((index === 5 && page.value >= 5) || (index === totalPage.value - 5)) {
								return <li key={index}>&hellip;</li>;
							}
							return null;
						}
					)}
					<li>
						<a role="button" onClick={() => pageChange(++page.value)}>
							<i class="fa fa-angle-double-right"></i>
						</a>
					</li>
				</ul>
			</div>
		);

	}
})
</script>

<style scoped lang="css">
@import "style.css";
</style>