import os

def print_folder_structure(start_path, indent=""):
    if not os.path.exists(start_path):
        print(f"Path '{start_path}' does not exist.")
        return

    items = sorted(os.listdir(start_path))
    for index, item in enumerate(items):
        path = os.path.join(start_path, item)
        is_last = index == len(items) - 1
        prefix = "└── " if is_last else "├── "
        print(indent + prefix + item)
        if os.path.isdir(path):
            extension = "    " if is_last else "│   "
            print_folder_structure(path, indent + extension)

if __name__ == "__main__":
    root_directory = "src"
    print(root_directory)
    print_folder_structure(root_directory)

