import solid from "solid-start/vite";
import { defineConfig } from "vite";

export default defineConfig({
  plugins: [solid({ ssr: false })],
  server: {
    proxy: {
      "/api": {
        target: "http://backend:8081",
        changeOrigin: true,
        secure: false,
        rewrite: path => path.replace(/^\/api/, ''),
        configure: (proxy, options) => {
          proxy.on('error', (err, _req, _res) => {
            console.log('proxy error', err);
          });
          proxy.on('proxyReq', (proxyReq, req, _res) => {
            console.log('Sending Request to the Target:', req.method, req.url);
          });
          proxy.on('proxyRes', (proxyResp, req, _res) => {
            console.log('Received Response from the Target:', proxyResp.statusCode, req.url);
          });
        }
      }
    },
    host: "0.0.0.0",
  },
});
