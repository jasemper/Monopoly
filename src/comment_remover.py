import os

def remove_comments_from_line(line):
    # Remove everything after and including '//' if it exists in the line
    comment_index = line.find("//")
    if comment_index != -1:
        return line[:comment_index].rstrip() + "\n"
    else:
        return line

def process_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    new_lines = [remove_comments_from_line(line) for line in lines]

    with open(file_path, 'w', encoding='utf-8') as f:
        f.writelines(new_lines)

def process_directory(root_dir):
    for dirpath, _, filenames in os.walk(root_dir):
        for filename in filenames:
            if filename.endswith('.scala'):
                full_path = os.path.join(dirpath, filename)
                print(f"Processing {full_path}")
                process_file(full_path)

if __name__ == "__main__":
    root_directory = "."  # or specify any directory path here
    process_directory(root_directory)

