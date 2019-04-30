import psutil

cpu_percent = psutil.cpu_percent(interval=1)
mem = psutil.virtual_memory()
disk = psutil.disk_usage('/')
info = {
    'cpu':cpu_percent,
    'mem_total': mem.total // 1024 // 1024 // 1024,
    'mem_percent': mem.percent,
    'disk_total': disk.total // 1024 // 1024 // 1024,
    'disk_percent': disk.percent
}
print(info)
