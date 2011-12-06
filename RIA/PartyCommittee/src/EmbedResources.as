package
{
	[Bindable]
	public class EmbedResources {
		public function EmbedResources() {
		}
		
		[Embed(source="assets/images/top_banner.swf")]
		public static const topBannerSWF:Class;
		
	}
}