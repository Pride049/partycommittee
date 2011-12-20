package
{
	[Bindable]
	public class EmbedResources {
		public function EmbedResources() {
		}
		
		[Embed(source="assets/images/top_banner.jpg")]
		public static const topBannerImg:Class;
		
	}
}