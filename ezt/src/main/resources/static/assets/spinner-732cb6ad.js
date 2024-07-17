import{r as o,j as u,al as O}from"./index-a2e5e034.js";var S={cm:!0,mm:!0,in:!0,px:!0,pt:!0,pc:!0,em:!0,ex:!0,ch:!0,rem:!0,vw:!0,vh:!0,vmin:!0,vmax:!0,"%":!0};function w(e){if(typeof e=="number")return{value:e,unit:"px"};var n,r=(e.match(/^[0-9.]*/)||"").toString();r.includes(".")?n=parseFloat(r):n=parseInt(r,10);var t=(e.match(/[^0-9]*$/)||"").toString();return S[t]?{value:n,unit:t}:(console.warn("React Spinners: ".concat(e," is not a valid css value. Defaulting to ").concat(n,"px.")),{value:n,unit:"px"})}function p(e){var n=w(e);return"".concat(n.value).concat(n.unit)}var j=function(e,n,r){var t="react-spinners-".concat(e,"-").concat(r);if(typeof window>"u"||!window.document)return t;var a=document.createElement("style");document.head.appendChild(a);var i=a.sheet,c=`
    @keyframes `.concat(t,` {
      `).concat(n,`
    }
  `);return i&&i.insertRule(c,0),t},l=globalThis&&globalThis.__assign||function(){return l=Object.assign||function(e){for(var n,r=1,t=arguments.length;r<t;r++){n=arguments[r];for(var a in n)Object.prototype.hasOwnProperty.call(n,a)&&(e[a]=n[a])}return e},l.apply(this,arguments)},E=globalThis&&globalThis.__rest||function(e,n){var r={};for(var t in e)Object.prototype.hasOwnProperty.call(e,t)&&n.indexOf(t)<0&&(r[t]=e[t]);if(e!=null&&typeof Object.getOwnPropertySymbols=="function")for(var a=0,t=Object.getOwnPropertySymbols(e);a<t.length;a++)n.indexOf(t[a])<0&&Object.prototype.propertyIsEnumerable.call(e,t[a])&&(r[t[a]]=e[t[a]]);return r},_=j("SyncLoader",`33% {transform: translateY(10px)}
  66% {transform: translateY(-10px)}
  100% {transform: translateY(0)}`,"sync");function k(e){var n=e.loading,r=n===void 0?!0:n,t=e.color,a=t===void 0?"#000000":t,i=e.speedMultiplier,c=i===void 0?1:i,d=e.cssOverride,y=d===void 0?{}:d,m=e.size,v=m===void 0?15:m,f=e.margin,g=f===void 0?2:f,h=E(e,["loading","color","speedMultiplier","cssOverride","size","margin"]),x=l({display:"inherit"},y),s=function(b){return{backgroundColor:a,width:p(v),height:p(v),margin:p(g),borderRadius:"100%",display:"inline-block",animation:"".concat(_," ").concat(.6/c,"s ").concat(b*.07,"s infinite ease-in-out"),animationFillMode:"both"}};return r?o.createElement("span",l({style:x},h),o.createElement("span",{style:s(1)}),o.createElement("span",{style:s(2)}),o.createElement("span",{style:s(3)})):null}const M=()=>u.jsxs(P,{children:[u.jsx("h3",{children:"잠시만 기다려주세요..."}),u.jsx(k,{color:"#1877f2",margin:5,size:10})]}),T=M,P=O.div`
  z-index: 999;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;export{T as S};
