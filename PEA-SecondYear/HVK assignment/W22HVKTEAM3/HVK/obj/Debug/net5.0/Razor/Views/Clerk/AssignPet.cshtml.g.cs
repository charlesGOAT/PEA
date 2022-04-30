#pragma checksum "E:\W22HVKTEAM3\HVK\Views\Clerk\AssignPet.cshtml" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "2a8f9e364cde7e86a1ae511c1461cac858063115"
// <auto-generated/>
#pragma warning disable 1591
[assembly: global::Microsoft.AspNetCore.Razor.Hosting.RazorCompiledItemAttribute(typeof(AspNetCore.Views_Clerk_AssignPet), @"mvc.1.0.view", @"/Views/Clerk/AssignPet.cshtml")]
namespace AspNetCore
{
    #line hidden
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Threading.Tasks;
    using Microsoft.AspNetCore.Mvc;
    using Microsoft.AspNetCore.Mvc.Rendering;
    using Microsoft.AspNetCore.Mvc.ViewFeatures;
#nullable restore
#line 1 "E:\W22HVKTEAM3\HVK\Views\_ViewImports.cshtml"
using HVK;

#line default
#line hidden
#nullable disable
#nullable restore
#line 2 "E:\W22HVKTEAM3\HVK\Views\_ViewImports.cshtml"
using HVK.Models;

#line default
#line hidden
#nullable disable
#nullable restore
#line 6 "E:\W22HVKTEAM3\HVK\Views\Clerk\AssignPet.cshtml"
using Newtonsoft.Json;

#line default
#line hidden
#nullable disable
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"2a8f9e364cde7e86a1ae511c1461cac858063115", @"/Views/Clerk/AssignPet.cshtml")]
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"db78992ba7dd6c67fd52ec2d3c0a363834cea22c", @"/Views/_ViewImports.cshtml")]
    #nullable restore
    public class Views_Clerk_AssignPet : global::Microsoft.AspNetCore.Mvc.Razor.RazorPage<IEnumerable<HVK.Models.PetReservation>>
    #nullable disable
    {
        private static readonly global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute __tagHelperAttribute_0 = new global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute("asp-action", "ConfirmAssignPet", global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
        private static readonly global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute __tagHelperAttribute_1 = new global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute("asp-controller", "Clerk", global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
        private static readonly global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute __tagHelperAttribute_2 = new global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute("asp-action", "Index", global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
        private static readonly global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute __tagHelperAttribute_3 = new global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute("class", new global::Microsoft.AspNetCore.Html.HtmlString("backbutton"), global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
        #line hidden
        #pragma warning disable 0649
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperExecutionContext __tagHelperExecutionContext;
        #pragma warning restore 0649
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperRunner __tagHelperRunner = new global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperRunner();
        #pragma warning disable 0169
        private string __tagHelperStringValueBuffer;
        #pragma warning restore 0169
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperScopeManager __backed__tagHelperScopeManager = null;
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperScopeManager __tagHelperScopeManager
        {
            get
            {
                if (__backed__tagHelperScopeManager == null)
                {
                    __backed__tagHelperScopeManager = new global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperScopeManager(StartTagHelperWritingScope, EndTagHelperWritingScope);
                }
                return __backed__tagHelperScopeManager;
            }
        }
        private global::Microsoft.AspNetCore.Mvc.TagHelpers.AnchorTagHelper __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper;
        #pragma warning disable 1998
        public async override global::System.Threading.Tasks.Task ExecuteAsync()
        {
#nullable restore
#line 7 "E:\W22HVKTEAM3\HVK\Views\Clerk\AssignPet.cshtml"
  
    Run runId = JsonConvert.DeserializeObject<Run>(ViewBag.runId);
    bool hasPetres = false;

#line default
#line hidden
#nullable disable
            WriteLiteral("<main>\r\n    <div class=\"assignpetdiv\">\r\n        <h2 class=\"manageReservation\">Select an Unassigned Pet</h2>\r\n        <div class=\"assignpetitems\">\r\n");
#nullable restore
#line 15 "E:\W22HVKTEAM3\HVK\Views\Clerk\AssignPet.cshtml"
             foreach (var petres in Model)
            {
                hasPetres = true;

#line default
#line hidden
#nullable disable
            WriteLiteral("                ");
            __tagHelperExecutionContext = __tagHelperScopeManager.Begin("a", global::Microsoft.AspNetCore.Razor.TagHelpers.TagMode.StartTagAndEndTag, "2a8f9e364cde7e86a1ae511c1461cac8580631155117", async() => {
                WriteLiteral("\r\n                    <div class=\"assignpetitem\">\r\n                        <h4>");
#nullable restore
#line 20 "E:\W22HVKTEAM3\HVK\Views\Clerk\AssignPet.cshtml"
                       Write(petres.Pet.Name);

#line default
#line hidden
#nullable disable
                WriteLiteral("</h4>\r\n                        <p>");
#nullable restore
#line 21 "E:\W22HVKTEAM3\HVK\Views\Clerk\AssignPet.cshtml"
                      Write(DisplayFormat.SizeString(petres.Pet.DogSize));

#line default
#line hidden
#nullable disable
                WriteLiteral("</p>\r\n                        <p>");
#nullable restore
#line 22 "E:\W22HVKTEAM3\HVK\Views\Clerk\AssignPet.cshtml"
                      Write(petres.Pet.Breed);

#line default
#line hidden
#nullable disable
                WriteLiteral("</p>\r\n");
#nullable restore
#line 23 "E:\W22HVKTEAM3\HVK\Views\Clerk\AssignPet.cshtml"
                         if (@petres.Pet.Barker)
                        {

#line default
#line hidden
#nullable disable
                WriteLiteral("                            <p>Barker</p>\r\n");
#nullable restore
#line 26 "E:\W22HVKTEAM3\HVK\Views\Clerk\AssignPet.cshtml"
                        }

#line default
#line hidden
#nullable disable
#nullable restore
#line 27 "E:\W22HVKTEAM3\HVK\Views\Clerk\AssignPet.cshtml"
                         if (petres.Pet.Climber)
                        {

#line default
#line hidden
#nullable disable
                WriteLiteral("                            <p>Climber</p>\r\n");
#nullable restore
#line 30 "E:\W22HVKTEAM3\HVK\Views\Clerk\AssignPet.cshtml"
                        }

#line default
#line hidden
#nullable disable
                WriteLiteral("                    </div>\r\n                ");
            }
            );
            __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper = CreateTagHelper<global::Microsoft.AspNetCore.Mvc.TagHelpers.AnchorTagHelper>();
            __tagHelperExecutionContext.Add(__Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper);
            __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.Action = (string)__tagHelperAttribute_0.Value;
            __tagHelperExecutionContext.AddTagHelperAttribute(__tagHelperAttribute_0);
            __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.Controller = (string)__tagHelperAttribute_1.Value;
            __tagHelperExecutionContext.AddTagHelperAttribute(__tagHelperAttribute_1);
            if (__Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.RouteValues == null)
            {
                throw new InvalidOperationException(InvalidTagHelperIndexerAssignment("asp-route-petResId", "Microsoft.AspNetCore.Mvc.TagHelpers.AnchorTagHelper", "RouteValues"));
            }
            BeginWriteTagHelperAttribute();
#nullable restore
#line 18 "E:\W22HVKTEAM3\HVK\Views\Clerk\AssignPet.cshtml"
                                                                                WriteLiteral(petres.PetReservationId);

#line default
#line hidden
#nullable disable
            __tagHelperStringValueBuffer = EndWriteTagHelperAttribute();
            __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.RouteValues["petResId"] = __tagHelperStringValueBuffer;
            __tagHelperExecutionContext.AddTagHelperAttribute("asp-route-petResId", __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.RouteValues["petResId"], global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
            BeginWriteTagHelperAttribute();
#nullable restore
#line 18 "E:\W22HVKTEAM3\HVK\Views\Clerk\AssignPet.cshtml"
                                                                                                                           WriteLiteral(runId.RunId);

#line default
#line hidden
#nullable disable
            __tagHelperStringValueBuffer = EndWriteTagHelperAttribute();
            __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.RouteValues["runId"] = __tagHelperStringValueBuffer;
            __tagHelperExecutionContext.AddTagHelperAttribute("asp-route-runId", __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.RouteValues["runId"], global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
            await __tagHelperRunner.RunAsync(__tagHelperExecutionContext);
            if (!__tagHelperExecutionContext.Output.IsContentModified)
            {
                await __tagHelperExecutionContext.SetOutputContentAsync();
            }
            Write(__tagHelperExecutionContext.Output);
            __tagHelperExecutionContext = __tagHelperScopeManager.End();
            WriteLiteral("\r\n");
#nullable restore
#line 33 "E:\W22HVKTEAM3\HVK\Views\Clerk\AssignPet.cshtml"
            }

#line default
#line hidden
#nullable disable
#nullable restore
#line 34 "E:\W22HVKTEAM3\HVK\Views\Clerk\AssignPet.cshtml"
             if(!hasPetres)
            {

#line default
#line hidden
#nullable disable
            WriteLiteral("                <p>There are no pets available to assign.</p>\r\n");
#nullable restore
#line 37 "E:\W22HVKTEAM3\HVK\Views\Clerk\AssignPet.cshtml"
            }

#line default
#line hidden
#nullable disable
            WriteLiteral("        </div>\r\n        <br />\r\n        <div class=\"assignback\">\r\n            ");
            __tagHelperExecutionContext = __tagHelperScopeManager.Begin("a", global::Microsoft.AspNetCore.Razor.TagHelpers.TagMode.StartTagAndEndTag, "2a8f9e364cde7e86a1ae511c1461cac85806311510745", async() => {
                WriteLiteral("Back");
            }
            );
            __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper = CreateTagHelper<global::Microsoft.AspNetCore.Mvc.TagHelpers.AnchorTagHelper>();
            __tagHelperExecutionContext.Add(__Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper);
            __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.Action = (string)__tagHelperAttribute_2.Value;
            __tagHelperExecutionContext.AddTagHelperAttribute(__tagHelperAttribute_2);
            __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.Controller = (string)__tagHelperAttribute_1.Value;
            __tagHelperExecutionContext.AddTagHelperAttribute(__tagHelperAttribute_1);
            __tagHelperExecutionContext.AddHtmlAttribute(__tagHelperAttribute_3);
            await __tagHelperRunner.RunAsync(__tagHelperExecutionContext);
            if (!__tagHelperExecutionContext.Output.IsContentModified)
            {
                await __tagHelperExecutionContext.SetOutputContentAsync();
            }
            Write(__tagHelperExecutionContext.Output);
            __tagHelperExecutionContext = __tagHelperScopeManager.End();
            WriteLiteral("\r\n        </div>\r\n    </div>\r\n</main>");
        }
        #pragma warning restore 1998
        #nullable restore
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public HVK.Models.FormattingService DisplayFormat { get; private set; } = default!;
        #nullable disable
        #nullable restore
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.ViewFeatures.IModelExpressionProvider ModelExpressionProvider { get; private set; } = default!;
        #nullable disable
        #nullable restore
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IUrlHelper Url { get; private set; } = default!;
        #nullable disable
        #nullable restore
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IViewComponentHelper Component { get; private set; } = default!;
        #nullable disable
        #nullable restore
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IJsonHelper Json { get; private set; } = default!;
        #nullable disable
        #nullable restore
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IHtmlHelper<IEnumerable<HVK.Models.PetReservation>> Html { get; private set; } = default!;
        #nullable disable
    }
}
#pragma warning restore 1591
